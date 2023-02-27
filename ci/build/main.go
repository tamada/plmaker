package main

import (
	"context"
	"fmt"
	"os"
	"strings"

	"dagger.io/dagger"
)

func findClasspath(cps []string, err error) string {
	libs := []string{}
	for _, str := range cps {
		libs = append(libs, "/home/gradle/build/libs/"+str)
	}
	return strings.Join(libs, ":")
}

func generateCompletion(ctx context.Context, client *dagger.Client) error {
	gradle := client.Container().From("gradle:jdk17").
		WithMountedDirectory("/home/gradle/build", client.Host().Directory("./build"))
	classpath := findClasspath(gradle.Directory("/home/gradle/build/libs").Entries(ctx))
	fmt.Printf("classpath: %s\n", classpath)

	gradle = gradle.WithExec([]string{"java", "-cp", classpath, "picocli.AutoComplete",
		"-n", "plmaker", "-o", "plmaker_completion",
		"-f", "jp.cafebabe.plmaker.cli.Main"})
	os.MkdirAll("/home/gradle/build/completions/bash", 0755)
	gradle.File("/home/gradle/plmaker_completion").Export(ctx, "./build/completions/bash/plmaker")
	return nil
}

func build(ctx context.Context, client *dagger.Client) error {
	gradle := client.Container().From("gradle:jdk17").
		WithMountedDirectory("/home/gradle", client.Host().Directory(".")).
		WithExec([]string{"gradle", "build"})
	output := gradle.Directory("/home/gradle/build")
	_, err := output.Export(ctx, "./build")
	if err != nil {
		return err
	}
	return nil
}

func reportToCoveralls(ctx context.Context, client *dagger.Client) error {
	gradle := client.Container().From("gradle:jdk17").
		WithMountedDirectory("/home/gradle", client.Host().Directory(".")).
		WithExec([]string{"gradle", "coveralls"})
	code, err := gradle.ExitCode(ctx)
	if err != nil {
		return err
	}
	if code != 0 {
		return fmt.Errorf("gradle coveralls: exit code was %d", code)
	}
	return nil
}

func goMain(args []string) int {
	ctx := context.Background()

	client, err := dagger.Connect(ctx, dagger.WithLogOutput(os.Stdout))
	if err != nil {
		fmt.Println(err.Error())
		return 1
	}
	defer client.Close()

	if err := build(ctx, client); err != nil {
		fmt.Printf("error: %s\n", err.Error())
		return 1
	}
	fmt.Printf("build project done")
	if err := generateCompletion(ctx, client); err != nil {
		fmt.Printf("error: %s\n", err.Error())
		return 2
	}
	fmt.Printf("generate completion file done")
	if err := reportToCoveralls(ctx, client); err != nil {
		fmt.Printf("error: %s\n", err.Error())
		return 3
	}
	fmt.Printf("report to coveralls done")
	return 0
}

func main() {
	status := goMain(os.Args)
	os.Exit(status)
}
