package main

import (
	"ci/ci"
	"context"
	"fmt"
	"os"
)

func findKind(args []string) ci.VersionFrom {
	if len(args) <= 1 {
		return ci.BranchName
	} else if args[1] == "tag" {
		return ci.LatestTag
	} else if args[1] == "gradle" {
		return ci.Gradle
	} else {
		return ci.BranchName
	}
}

func goMain(args []string) int {
	kind := findKind(args)
	version, err := ci.GetVersion(context.Background(), kind)
	if err != nil {
		fmt.Printf("%s\n", err.Error())
		return 1
	}
	fmt.Println(version)
	return 0
}

func main() {
	status := goMain(os.Args)
	os.Exit(status)
}
