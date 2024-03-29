package hu.simplexion.z2.content.impl

import hu.simplexion.z2.auth.context.ensureInternal
import hu.simplexion.z2.auth.context.ensuredByLogic
import hu.simplexion.z2.content.api.ContentApi
import hu.simplexion.z2.content.impl.upload.ChunkData
import hu.simplexion.z2.content.impl.upload.Upload
import hu.simplexion.z2.content.impl.upload.UploadAbortException
import hu.simplexion.z2.content.model.Content
import hu.simplexion.z2.content.model.ContentStatus
import hu.simplexion.z2.content.table.ContentTable.Companion.contentTable
import hu.simplexion.z2.services.ServiceImpl
import hu.simplexion.z2.services.get
import hu.simplexion.z2.setting.dsl.setting
import hu.simplexion.z2.util.UUID
import java.nio.file.Path
import java.nio.file.Paths

open class ContentImpl : ContentApi, ServiceImpl<ContentImpl> {

    companion object {
        /**
         * The file system path to the directory to store content files.
         */
        val contentPath by setting<Path> { "CONTENT_PATH" } default Paths.get("./var/tmp")

        var globalContentPlacementStrategy: ContentPlacementStrategy = BasicPlacementStrategy(contentPath)

        val contentImpl = ContentImpl().internal

        var traceUpload : Boolean = false
    }

    val placementStrategy: ContentPlacementStrategy
        get() = globalContentPlacementStrategy

    fun ensureUpload(uuid: UUID<Content>, block: Upload.() -> Unit) {
        ensuredByLogic("the service context contains the upload only when this context started it")
        serviceContext[uuid.cast<Upload>()]?.apply { block() } ?: throw UploadAbortException()
    }

    /**
     * Start a content upload. This function is meant to be called internally by other modules
     * that provide client API for creating content in some context. Most cases, content is
     * uploaded in some context that defines what kind of content and where to upload. The
     * caller of this function should handle that context.
     */
    fun startUpload(content: Content): Upload {
        ensureInternal()

        content.status = ContentStatus.Uploading
        content.uuid = contentTable.insert(content)

        val upload = Upload(
            content.uuid.cast(),
            content.size,
            placementStrategy.dataPathOf(content),
            placementStrategy.statusPathOf(content)
        )

        upload.start()

        return upload
    }

    override suspend fun uploadChunk(uuid: UUID<Content>, position: Long, bytes: ByteArray) {
        ensureUpload(uuid) {
            add(ChunkData(uuid, position, bytes))
        }
    }

    override suspend fun cancelUpload(uuid: UUID<Content>) {
        ensureUpload(uuid) {
            abort(false)
            contentTable.cancelUpload(uuid)
        }
    }

    override suspend fun closeUpload(uuid: UUID<Content>, sha256: String) {
        ensureUpload(uuid) {
            close(sha256)
            contentTable.closeUpload(uuid, sha256)
        }
    }

    override suspend fun rename(uuid: UUID<Content>, name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(uuid: UUID<Content>) {
        TODO("Not yet implemented")
    }

    override suspend fun getDownloadLink(uuid: UUID<Content>): String {
        TODO("Not yet implemented")
    }

    fun getPath(uuid: UUID<Content>) =
        placementStrategy.dataPathOf(Content().also { it.uuid = uuid })

}