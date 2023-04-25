package social.aceinpink.model

import java.util.Date


data class InviteCodeDetail(
    /**
     * The actual code string.
     */
    val code: String,

    /**
     * The number of available uses left for this code.
     */
    val available: Int,

    /**
     * Whether this code is disabled or not.
     */
    val disabled: Boolean,

    /**
     * The account that owns the code.
     */
    val forAccount: String,

    /**
     * What account initiated the code creation.
     */
    val createdBy: String,

    /**
     * When the code was created.
     */
    val createdAt: Date,

    /**
     * All the times the code has been used.
     */
    val uses: List<Use>
) {
    data class Use(
        val usedBy: String,
        val usedAt: Date,
    )
}