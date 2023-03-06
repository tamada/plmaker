package ci

import (
	"bufio"
	"io"
	"os"
	"strings"
)

type Replacer struct {
	OldPart string
	NewPart string
}

func Sed(fromFile, toFile string, r []*Replacer) error {
	in, out, err := openFile(fromFile, toFile)
	if err != nil {
		return err
	}
	defer in.Close()
	defer out.Close()
	return doSed(in, out, r)
}

func doSed(in io.Reader, out io.Writer, r []*Replacer) error {
	reader := bufio.NewReader(in)
	writer := bufio.NewWriter(out)
	for {
		row, err := reader.ReadString('\n')
		if err != nil && err != io.EOF {
			return err
		}
		if err == io.EOF && len(row) == 0 {
			break
		}
		result := filterFunc(row, r)
		_, err = writer.WriteString(result)
		if err != nil {
			return err
		}
	}
	return nil

}

func filterFunc(line string, replacers []*Replacer) string {
	for _, r := range replacers {
		line = strings.ReplaceAll(line, r.OldPart, r.NewPart)
	}
	return line
}

func openFile(fromFile, toFile string) (io.ReadCloser, io.WriteCloser, error) {
	in, err := os.Open(fromFile)
	if err != nil {
		return nil, nil, err
	}
	out, err := os.Create(toFile)
	if err != nil {
		defer in.Close()
		return nil, nil, err
	}
	return in, out, err
}
