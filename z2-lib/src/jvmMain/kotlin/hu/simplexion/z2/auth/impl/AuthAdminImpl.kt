package hu.simplexion.z2.auth.impl

import hu.simplexion.z2.application.applicationSettings
import hu.simplexion.z2.application.securityOfficerRole
import hu.simplexion.z2.auth.api.AuthAdminApi
import hu.simplexion.z2.auth.context.ensureAny
import hu.simplexion.z2.auth.context.publicAccess
import hu.simplexion.z2.auth.model.SecurityPolicy
import hu.simplexion.z2.auth.util.runAsSecurityOfficer
import hu.simplexion.z2.schematic.ensureValid
import hu.simplexion.z2.services.ServiceImpl
import hu.simplexion.z2.setting.settingImpl

class AuthAdminImpl : AuthAdminApi, ServiceImpl<AuthAdminImpl> {

    companion object {
        val authAdminImpl = AuthAdminImpl().internal

        val KEY = "securityPolicy"

        var policySetRoles = arrayOf(securityOfficerRole.uuid)
    }

    override suspend fun getPolicy(): SecurityPolicy {
        publicAccess() // policy needed for account activation, also it does not contain sensitive information
        return runAsSecurityOfficer { so ->
            settingImpl(so).get(applicationSettings.applicationUuid, KEY, SecurityPolicy())
        }
    }

    override suspend fun setPolicy(policy: SecurityPolicy) {
        ensureAny(*policySetRoles)
        ensureValid(policy)
        runAsSecurityOfficer { so ->
            settingImpl(so).put(applicationSettings.applicationUuid, KEY, policy)
        }
    }

}