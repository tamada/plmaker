package ci

import (
	"archive/tar"
	"archive/zip"
	"bufio"
	"compress/gzip"
	"fmt"
	"io"
	"io/fs"
	"os"
	"path/filepath"
	"strings"
)

type ArchiveType int

const (
	DIR ArchiveType = iota
	TAR_GZ
	TAR_BZ2
	ZIP
)

type acceptor struct {
	visitor VisitFunc
	walker  filepath.WalkFunc
}

type VisitFunc func(path string, info fs.FileInfo) error

func TrimExtension(outputFileName string) string {
	if strings.HasSuffix(outputFileName, ".tar.gz") {
		return strings.TrimSuffix(outputFileName, ".tar.gz")
	} else if strings.HasSuffix(outputFileName, ".tgz") {
		return strings.TrimSuffix(outputFileName, ".tgz")
	} else if strings.HasSuffix(outputFileName, ".tar.bz2") {
		return strings.TrimSuffix(outputFileName, ".tar.bz2")
	} else if strings.HasSuffix(outputFileName, ".tbz2") {
		return strings.TrimSuffix(outputFileName, ".tbz2")
	} else if strings.HasSuffix(outputFileName, ".zip") {
		return strings.TrimSuffix(outputFileName, ".zip")
	}
	return outputFileName
}

func parseType(fileName string) ArchiveType {
	if strings.HasSuffix(fileName, ".tar.gz") || strings.HasSuffix(fileName, ".tgz") {
		return TAR_GZ
	} else if strings.HasSuffix(fileName, ".tar.bz2") || strings.HasSuffix(fileName, "tbz") {
		return TAR_BZ2
	} else if strings.HasSuffix(fileName, ".zip") {
		return ZIP
	}
	return DIR
}

func New(visitor VisitFunc) *acceptor {
	a := &acceptor{}
	a.visitor = visitor
	a.walker = func(path string, info fs.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.Mode().IsRegular() {
			return a.visitor(path, info)
		}
		return nil
	}
	return a
}

func BuildArchive(dest string, dir string) error {
	switch parseType(dest) {
	case TAR_GZ:
		return createTarGz(dest, dir)
	case TAR_BZ2:
		return createTarBz2(dest, dir)
	case ZIP:
		return createZip(dest, dir)
	case DIR:
		return move(dest, dir)
	}
	return nil
}

func move(dest string, dir string) error {
	return os.Rename(dir, dest)
}

func createTarGz(dest string, dir string) error {
	writer, err := os.Create(dest)
	if err != nil {
		return err
	}
	defer writer.Close()
	gzip := gzip.NewWriter(writer)
	defer gzip.Close()
	return createTar(gzip, dir)
}

func createTar(writer io.Writer, dir string) error {
	out := tar.NewWriter(writer)
	defer out.Close()
	base := filepath.Dir(dir)
	a := New(func(file string, info fs.FileInfo) error {
		target, err := filepath.Rel(base, file)
		if err != nil {
			return err
		}
		header := &tar.Header{
			Name:    target,
			Mode:    int64(info.Mode()),
			Size:    info.Size(),
			ModTime: info.ModTime(),
		}
		if err := out.WriteHeader(header); err != nil {
			return err
		}
		in, err := os.Open(file)
		if err != nil {
			return err
		}
		defer in.Close()
		if _, err := io.Copy(out, in); err != nil {
			return err
		}
		return nil
	})
	return filepath.Walk(dir, a.walker)
}

func createTarBz2(dest string, dir string) error {
	return fmt.Errorf("bzip2: does not implemented yet")
}

func createZip(dest string, dir string) error {
	writer, err := os.Create(dest)
	if err != nil {
		return err
	}
	defer writer.Close()
	zip := zip.NewWriter(writer)
	defer zip.Close()
	base := filepath.Dir(dir)
	a := New(func(file string, info fs.FileInfo) error {
		target, err := filepath.Rel(base, file)
		if err != nil {
			return err
		}
		writer, err := zip.Create(target)
		if err != nil {
			return err
		}
		return sink(file, writer)
	})
	return filepath.Walk(dir, a.walker)
}

func sink(from string, dest io.Writer) error {
	source, err := os.Open(from)
	if err != nil {
		return err
	}
	w := bufio.NewWriter(dest)
	_, err = w.ReadFrom(source)
	return err
}
