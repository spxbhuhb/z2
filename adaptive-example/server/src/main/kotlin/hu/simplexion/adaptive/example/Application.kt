package hu.simplexion.adaptive.example

import hu.simplexion.adaptive.base.adaptive
import hu.simplexion.adaptive.example.service.CounterService
import hu.simplexion.adaptive.example.worker.CounterWorker
import hu.simplexion.adaptive.ktor.KtorWorker
import hu.simplexion.adaptive.server.AdaptiveServerAdapter
import hu.simplexion.adaptive.server.builtin.service
import hu.simplexion.adaptive.server.builtin.worker
import hu.simplexion.adaptive.server.setting.dsl.inline
import hu.simplexion.adaptive.server.setting.dsl.settings

fun main() {

   adaptive(AdaptiveServerAdapter<Any>(true)) {

       settings {
           inline(
               "KTOR_PORT" to 8080,
               "KTOR_STATIC" to "../browserApp/build/processedResources/js/main"
           )
       }

       service { CounterService() }
       worker { CounterWorker() }

       worker { KtorWorker() }

   }

}