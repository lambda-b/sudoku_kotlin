
ARG VARIANT
FROM mcr.microsoft.com/devcontainers/java:${VARIANT}

#作成する環境の時間設定を日本時間にしておきます。これにより、ビルドが中断されなくなります。
ENV TZ=Asia/Tokyo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN bash -c "mkdir -p /usr/local/sdkman/candidates/java"
RUN bash -c "ln -s $JAVA_HOME /usr/local/sdkman/candidates/java/current"

CMD ["bash"]
