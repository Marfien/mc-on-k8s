variable "VERSION" {
  default = "0.0.0"
}

variable "LATEST" {
  default = false
}

variable "BASE_REPO" {
  default = "ghcr.io/marfien/mc-on-k8s"
}

group "default" {
  targets = ["proxy", "gameserver"]
}

group "proxy" {
  targets = ["proxy-velocity", "proxy-bungeecord"]
}

group "gameserver" {
  targets = ["gameserver-bukkit", "gameserver-sponge", "gameserver-minestom"]
}

target "proxy-velocity" {
  context = "./proxy-impl-velocity/"
  tags = makeTags("proxy", "velocity")
}

target "proxy-bungeecord" {
  context = "./proxy-impl-bungeecord/"
  tags = makeTags("proxy", "bungeecord")
}

target "gameserver-bukkit" {
  context = "./server-impl-bukkit/"
  tags = makeTags("server", "bukkit")
}

target "gameserver-minestom" {
  context = "./server-impl-minestom/"
  tags = makeTags("server", "minestom")
}

target "gameserver-sponge-vanilla" {
  context = "./server-impl-sponge/"
  dockerfile = "vanilla.Dockerfile"
  tags = makeTags("server", "sponge-vanilla")
}

target "gameserver-sponge-forge" {
  context = "./server-impl-sponge/"
  dockerfile = "forge.Dockerfile"
  tags = makeTags("server", "sponge-forge")
}

function "makeTags" {
  params = [type, name]
  result = [
    makeTag(type, "${name}-${VERSION}"),
    LATEST ? makeTag(type, name) : "",
    LATEST ? makeTag(type, "${name}-latest") : ""
  ]
}

function "makeTag" {
  params = [name, tag]
  result = "${BASE_REPO}/${name}:${tag}"
}