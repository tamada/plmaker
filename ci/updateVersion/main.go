package main

import (
	"ci/ci"
	"context"
	"fmt"
	"io/fs"
	"os"
	"path/filepath"
	"strings"
)

func updateFile(fromFile, toFile, toVersion string) error {
	replacers := []*ci.Replacer{
		{OldPart: "${VERSION}", NewPart: toVersion},
		{OldPart: "${VERSION_DH}", NewPart: strings.ReplaceAll(toVersion, "-", "--")},
	}
	return ci.Sed(fromFile, toFile, replacers)
}

func doUpdate(ctx context.Context, toVersion string) error {
	return filepath.Walk(".templates", func(path string, info fs.FileInfo, err error) error {
		if err != nil {
			return err
		}
		rel, err := filepath.Rel(".templates", path)
		if err != nil {
			return err
		}
		return updateFile(path, rel, toVersion)
	})
}

func updateVersion(ctx context.Context) error {
	version, err := ci.GetVersion(ctx, ci.BranchName)
	if err != nil {
		return err
	}
	return doUpdate(ctx, version)
}

func goMain(args []string) int {
	ctx := context.Background()
	err := updateVersion(ctx)
	if err != nil {
		fmt.Printf("UpdateVersion: %s\n", err.Error())
		return 1
	}
	return 0
}

func main() {
	status := goMain(os.Args)
	os.Exit(status)
}
