package ci

import (
	"errors"
	"fmt"
	"io"
	"os"
	"path/filepath"
)

func DeleteDir(path string) int {
	if err := os.RemoveAll(path); err != nil {
		fmt.Println(err.Error())
		return 1
	}
	return 0
}

func Copy(destDir string, files ...string) error {
	errs := []error{}
	for _, file := range files {
		destPath := filepath.Join(destDir, file)
		in, err := os.Open(file)
		if err != nil {
			errs = append(errs, err)
		}
		defer in.Close()
		out, err := os.Create(destPath)
		if err != nil {
			errs = append(errs, err)
		}
		defer out.Close()
		io.Copy(out, in)
	}
	return errors.Join(errs...)
}
