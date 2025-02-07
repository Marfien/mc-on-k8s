allprojects {
    val groupId: String by project
    val version: String by project

    this.group = groupId
    this.version = version
}
