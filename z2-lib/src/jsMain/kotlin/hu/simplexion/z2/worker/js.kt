package hu.simplexion.z2.worker

import hu.simplexion.z2.services.getService
import hu.simplexion.z2.worker.api.WorkerApi

val workers = getService<WorkerApi>()

fun workerJs() {
    workerCommon()
}