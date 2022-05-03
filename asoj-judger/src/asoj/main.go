package main

import (
	"asoj/application/stdio"
	"net/http"
	"os"
)

func main() {
	startService(addr)
}

const (
	basePattern = "/judge"
	addr        = ":8000"
)

func registerApi(pattern string, handler func(http.ResponseWriter, *http.Request)) {
	http.HandleFunc(basePattern+pattern, handler)
}

func startService(addr string) {
	err := http.ListenAndServe(addr, nil)
	if err != nil {
		stdio.LogAssert("", "服务启动失败", err)
		os.Exit(0)
	}
}
