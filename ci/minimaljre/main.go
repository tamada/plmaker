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

type options struct {
	modulepath string
	modules    string
	launcher   string
	output     string
	tmpPath    string
}

func (o *options) Output() string {
	parent := os.TempDir()
	path := ci.TrimExtension(o.output)
	o.tmpPath = filepath.Join(parent, path)
	return o.tmpPath
}

func perform(opts *options, args []string) int {
	status := createMinimalJre(opts, args)
	if status != 0 {
		return status
	}
	if err := ci.Copy(opts.tmpPath, "README.md", "LICENSE"); err != nil {
		fmt.Printf("copy: %s\n", err.Error())
		return 1
	}
	if err := copy.Copy("build/completions", filepath.Join(opts.tmpPath, "completions")); err != nil {
		fmt.Printf("copy: %s\n", err.Error())
		return 2
	}
	if err := ci.BuildArchive(opts.output, opts.Output()); err != nil {
		fmt.Printf("%s: error (%s)\n", opts.output, err.Error())
		return 4
	}
	return ci.DeleteDir(opts.tmpPath)
}

func createMinimalJre(opts *options, args []string) int {
	arguments := []string{"--module-path", opts.modules, "--compress=2",
		"--no-header-files", "--no-man-pages", "--add-modules", opts.modules, "--verbose",
		"--output", opts.Output()}
	if opts.launcher != "" {
		arguments = append(arguments, "--launcher")
		arguments = append(arguments, opts.launcher)
	}
	if opts.modulepath != "" {
		arguments = append(arguments, "--module-path")
		arguments = append(arguments, opts.modulepath)
	}
	fmt.Printf("jlink %s\n", strings.Join(arguments, " "))
	out, err := exec.Command("jlink", arguments...).Output()
	if err != nil {
		fmt.Printf("error jlink: %s\n", err.Error())
		return 1
	}
	fmt.Println(string(out))
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

func createFlags(args []string) (*flag.FlagSet, *options, error) {
	flags := flag.NewFlagSet("minimaljre_builder", flag.ContinueOnError)
	opts := &options{}
	flags.StringVarP(&opts.modules, "add-modules", "m", "java.base", "specify the modules for including the resultant jre")
	flags.StringVarP(&opts.output, "asset-name", "a", "./minimaljre", "specify the destination directory. default is minimaljre")
	flags.StringVarP(&opts.launcher, "launcher", "L", "", "specify the launcher option for jlink")
	flags.StringVarP(&opts.modulepath, "module-path", "p", "", "specify the modules the module paths for minimaljre")
	err := flags.Parse(args)
	return flags, opts, err
}

func main() {
	status := goMain(os.Args)
	os.Exit(status)
}
