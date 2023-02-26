package main

import (
	"ci/ci"
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"strings"

	"github.com/otiai10/copy"
	flag "github.com/spf13/pflag"
)

type NativeImageOption struct {
	archiveName    string
	binaryName     string
	classpath      string
	jarFile        string
	resourceConfig string
	reflectConfig  string
	proxyConfig    string
}

func perform(opts *NativeImageOption, args []string) int {
	tempDir := os.TempDir()
	status := createNativeImage(opts, args)
	if status != 0 {
		return status
	}
	destTempDir := filepath.Join(tempDir, ci.TrimExtension(opts.archiveName))
	os.Mkdir(destTempDir, 0755)
	if err := ci.Copy(destTempDir, "README.md", "LICENSE", opts.binaryName); err != nil {
		fmt.Printf("copy: %s\n", err.Error())
		return 1
	}
	if err := copy.Copy("build/completions", filepath.Join(destTempDir, "completions")); err != nil {
		fmt.Printf("copy: %s\n", err.Error())
		return 2
	}
	if err := ci.BuildArchive(opts.archiveName, destTempDir); err != nil {
		fmt.Printf("%s: error (%s)\n", opts.archiveName, err.Error())
		return 4
	}
	return ci.DeleteDir(destTempDir)
}

func appendConfig(args []string, value, key string) []string {
	if value == "" {
		return args
	}
	return append(args, fmt.Sprintf("-H:%sConfigurationFiles=%s", key, value))
}

func createNativeImage(opts *NativeImageOption, args []string) int {
	arguments := []string{"-jar", opts.jarFile, "--class-path", opts.classpath,
		"--no-fallback", "--enable-http", "--enable-https", "-H:+ReportExceptionStackTraces",
		"-H:Log=registerResource:5",
	}
	arguments = appendConfig(arguments, opts.proxyConfig, "Proxy")
	arguments = appendConfig(arguments, opts.reflectConfig, "Reflection")
	arguments = appendConfig(arguments, opts.resourceConfig, "Resource")
	arguments = append(arguments, opts.binaryName)
	fmt.Printf("native-image %s\n", strings.Join(arguments, " "))
	cmd := exec.Command("native-image", arguments...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	if err := cmd.Run(); err != nil {
		fmt.Printf("error native-image: %s\n", err.Error())
		return 1
	}
	return 0
}

// This program should run on the various platform provided by GitHub Actions.
func goMain(args []string) int {
	flags, opts, err := createFlags(args)
	if err != nil {
		fmt.Printf("error: %s\n", err.Error())
		return 1
	}
	return perform(opts, flags.Args())
}

func createFlags(args []string) (*flag.FlagSet, *NativeImageOption, error) {
	flags := flag.NewFlagSet("minimaljre_builder", flag.ContinueOnError)
	opts := &NativeImageOption{}
	flags.StringVarP(&opts.archiveName, "asset-name", "a", "", "specify the resultant archive name")
	flags.StringVarP(&opts.binaryName, "binary-name", "b", "", "specify the resultant binary name from native-image")
	flags.StringVarP(&opts.classpath, "classpath", "c", "", "specify the classpath list for native-image")
	flags.StringVarP(&opts.jarFile, "jar", "j", "", "specify the jar file name for main class")
	flags.StringVarP(&opts.proxyConfig, "proxy-config", "p", "", "specify the proxy config file for native-image")
	flags.StringVarP(&opts.reflectConfig, "reflect-config", "r", "", "specify the reflect config file for native-image")
	flags.StringVarP(&opts.resourceConfig, "resource-config", "R", "", "specify the resource config file for native-image")
	err := flags.Parse(args)
	return flags, opts, err
}

func main() {
	status := goMain(os.Args)
	os.Exit(status)
}
