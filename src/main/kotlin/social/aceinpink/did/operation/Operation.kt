package social.aceinpink.did.operation

interface Operation {
    val rotationKeys: List<String>
    val prev: Nothing?
}