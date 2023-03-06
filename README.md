# plmaker


[![codecov](https://codecov.io/gh/tamada/plmaker/branch/main/graph/badge.svg?token=21RRMZ1U1W)](https://codecov.io/gh/tamada/plmaker)

[![Version](https://img.shields.io/badge/Version-v0.6.3-blue)](https://github.com/tamada/plmaker/releases/tag/v${VERSION)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue)](https://github.com/tamada/plmaker/blob/main/LICENSE)

![Docker](https://img.shields.io/static/v1?label=Docker&message=ghcr.io/tamada/plmaker/native:0.6.3&color=green&logo=docker)
![Docker](https://img.shields.io/static/v1?label=Docker&message=ghcr.io/tamada/plmaker/java:0.6.3&color=green&logo=docker)

[![Homebrew](https://img.shields.io/badge/Homebrew-tamada/brew/plmaker-green?logo=homebrew)](https://github.com/tamada/homebrew-brew)

## :speaking_head: Overview

`plmaker` is an abbreviation for "Product Label Maker."
This tool retrieves project information from GitHub.
When creating a website to introduce related products,
it is necessary to collect information about the products.
However, the timing of web page creation and product updates often differ,
resulting in outdated information on the website.

`plmaker` solves this problem by collecting product information from GitHub and
providing a summary in JSON format.
This collected information can be easily converted for use in websites built with
popular static site generators such as Hugo, Jekyll, Hexo, and others.


## :runner: Usage

### :bike: CLI

```sh
Usage: plmaker [-hV] [--overwrite] [--store=STORE_TYPE] [-t=TOKEN] PROJECTs...
      PROJECTs...          The product name in GitHub. The name should form
                             OWNER/PRODUCT.
  -h, --help               Show this help message and exit.
      --overwrite          If this value is true, plmaker overwrite the
                             configuration file.
      --store=STORE_TYPE   If the value of this option is HOME or CWD, after
                             running the plmaker, store the config file to the
                             specified location. Default: NONE, Available:
                             HOME, CWD, NONE
  -t, --token=TOKEN        Specify the token for the GitHub API.  This product
                             reads GitHub token from this option value, 
                             ./.plmaker.json, and ~/.config/plmaker/config.json
                             in this order.
  -V, --version            Print version information and exit.
```

### :whale: Docker

* native image
  * ![Docker](https://img.shields.io/static/v1?label=Docker&message=ghcr.io/tamada/plmaker/native:0.6.3&color=green&logo=docker)
* Java (minimal JRE)
  * ![Docker](https://img.shields.io/static/v1?label=Docker&message=ghcr.io/tamada/plmaker/java:0.6.3&color=green&logo=docker)

```shell
docker run -it --rm -v $PWD:/app ghcr.io/tamada/plmaker/native:0.6.3 <ARGUMENTS_FOR_PLMAKER>
```
  
## :anchor: Install

### Package Manager

* :beer: Homebrew
  * [![Homebrew](https://img.shields.io/badge/Homebrew-tamada/brew/plmaker-green?logo=homebrew)](https://github.com/tamada/homebrew-brew)
  * `brew install tamada/brew/plmaker`


### :muscle: Compiling yourself

Type the following commands, then `build/libs/plmaker-0.6.3.jar` will be built.

```shell
 git clone https://github.com/tamada/plmaker.git
 cd plmaker
 gradle build
```

## :smile: About

### :scroll: License

[![License](https://img.shields.io/badge/License-Apache--2.0-blue)](https://github.com/tamada/plmaker/blob/main/LICENSE)

### :man_office_worker: Developers :woman_office_worker:

* Haruaki TAMADA.


