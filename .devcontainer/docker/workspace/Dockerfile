# windows 11
FROM ubuntu:22.04

ARG USER_NAME
ARG USER_GROUP_NAME
ARG USER_UID
ARG USER_GID

# general packages
ARG PKG="vim curl unzip zip sudo tzdata git net-tools lsof groff"

SHELL ["/bin/bash", "-c"]

# setup timezone https://dev.to/grigorkh/fix-tzdata-hangs-during-docker-image-build-4o9m
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo TZ > /etc/timezone

RUN apt-get update \
    && apt-get install -y ${PKG} \
    && groupadd --gid ${USER_GID} ${USER_GROUP_NAME} \
    && useradd --uid ${USER_UID} --shell /bin/bash --gid ${USER_GID} -m ${USER_NAME} \
    && echo %${USER_GROUP_NAME} ALL=\(ALL\) NOPASSWD:ALL > /etc/sudoers.d/${USER_GROUP_NAME} \
    && chmod 0440 /etc/sudoers.d/${USER_GROUP_NAME}

# enable git
# https://github.com/devcontainers/cli/issues/98
# change to main branch https://dev-yakuza.posstree.com/git/default-branch/
RUN su ${USER_NAME} --command \
    'sudo git config --system --add safe.directory "/home/${USER_NAME}/workspace"'

# ---------------------------------- sdkman ----------------------------------
# https://sdkman.io/install
ARG JAVA_VERSION
ARG GRADLE_VERSION

RUN su ${USER_NAME} --command \
    'curl -s "https://get.sdkman.io" | bash \
    && source "${HOME}/.sdkman/bin/sdkman-init.sh" \
    && sdk install java "${JAVA_VERSION}" \
    && sdk install gradle ${GRADLE_VERSION}'

# ---------------------------------- aws cli ----------------------------------

ARG ZIP_FILE=awscliv2.zip

RUN apt-get install -y less \
    && curl https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip -o ${ZIP_FILE} \
    && unzip ${ZIP_FILE}  \
    && ./aws/install \
    && rm -f ${ZIP_FILE} \
    && echo "complete -C '/usr/local/bin/aws_completer' aws" >> ${HOME}/.bashrc \
    && source ${HOME}/.bashrc


# ---------------------------------- set up language ----------------------------------
# setup locale https://stackoverflow.com/a/55077451/13723015
ARG LOCALE

RUN apt-get -y install locales \
    && echo "LC_ALL=${LOCALE}" >> /etc/environment \
    && echo "${LOCALE} UTF-8" >> /etc/locale.gen \
    && echo "LANG=${LOCALE}" > /etc/locale.conf \
    && locale-gen ${LOCALE}

# ---------------------------------- github cli ----------------------------------

RUN curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg \
    && chmod go+r /usr/share/keyrings/githubcli-archive-keyring.gpg \
    && echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | tee /etc/apt/sources.list.d/github-cli.list > /dev/null \
    && apt-get update \
    && apt-get install gh -y \
    && gh --version