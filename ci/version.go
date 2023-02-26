package ci

import (
	"context"
	"fmt"
	"os"
	"strings"

	"dagger.io/dagger"
	"golang.org/x/mod/semver"
)

type VersionFrom int

const (
	BranchName VersionFrom = iota + 1
	LatestTag
	Gradle
)

func (vf VersionFrom) String() string {
	if vf == BranchName {
		return "branch_name"
	} else if vf == LatestTag {
		return "latest_tag"
	} else if vf == Gradle {
		return "gradle"
	}
	return "unknown"
}

func GetVersion(ctx context.Context, from VersionFrom) (string, error) {
	if from == Gradle {
		return getVersionFromGradle(ctx)
	} else {
		return getVersionWithGit(ctx, from)
	}
}

func getVersionFromGradle(ctx context.Context) (string, error) {
	client, err := dagger.Connect(ctx, dagger.WithLogOutput(os.Stdout))
	if err != nil {
		return "", err
	}
	defer client.Close()
	gradle := client.Container().
		From("gradle:jdk17").
		WithMountedDirectory("/home/gradle", client.Host().Directory(".")).
		WithExec([]string{"gradle", "--quiet", "printVersion"})
	result, err := gradle.Stdout(ctx)
	if result == "" && err == nil {
		return "", fmt.Errorf("no version was obtained")
	}
	return "v" + strings.TrimSpace(result), err
}

func getVersionWithGit(ctx context.Context, from VersionFrom) (string, error) {
	client, err := dagger.Connect(ctx, dagger.WithLogOutput(os.Stdout))
	if err != nil {
		return "", err
	}
	defer client.Close()
	git := client.Container().
		From("alpine/git:latest").
		WithMountedDirectory("/git", client.Host().Directory(".")).
		WithWorkdir("/git")
	switch from {
	case BranchName:
		return findVersionFromBranchName(ctx, git)
	case LatestTag:
		return findVersionFromLatestTag(ctx, git)
	default:
		return "", fmt.Errorf("%s: unknown VersionFrom", from.String())
	}
}

func findVersionFromLatestTag(ctx context.Context, git *dagger.Container) (string, error) {
	tagContainer := git.WithExec([]string{"tag", "--list"})
	tagString, err := tagContainer.Stdout(ctx)
	if err != nil {
		return "", err
	}
	tags := strings.Split(tagString, "\n")
	semver.Sort(tags)
	latest := tags[len(tags)-1]
	if semver.IsValid(latest) {
		return latest, nil
	}
	return "", fmt.Errorf("%s: invalid semantic versioning format", latest)
}

func findVersionFromBranchName(ctx context.Context, git *dagger.Container) (string, error) {
	branchContainer := git.WithExec([]string{"branch", "--show-current"})
	versionString, err := branchContainer.Stdout(ctx)
	if err != nil {
		return "", err
	}
	versionString = strings.TrimSpace(versionString)
	if strings.HasPrefix(versionString, "releases/v") {
		return strings.TrimPrefix(versionString, "releases/"), nil
	}
	return "", fmt.Errorf("%s: does not have version string", versionString)
}
