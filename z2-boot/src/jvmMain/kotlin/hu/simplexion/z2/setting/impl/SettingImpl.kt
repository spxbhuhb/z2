package hu.simplexion.z2.setting.impl

import hu.simplexion.z2.auth.context.ensureSelfOrSecurityOfficer
import hu.simplexion.z2.auth.model.Principal
import hu.simplexion.z2.baseStrings
import hu.simplexion.z2.commons.util.UUID
import hu.simplexion.z2.history.util.settingHistory
import hu.simplexion.z2.localization.text.commonStrings
import hu.simplexion.z2.service.ServiceImpl
import hu.simplexion.z2.setting.api.SettingApi
import hu.simplexion.z2.setting.model.Setting
import hu.simplexion.z2.setting.table.SettingTable.Companion.settingTable

class SettingImpl : SettingApi, ServiceImpl<SettingImpl> {

    companion object {
        val settingImpl = SettingImpl().internal
    }

    override suspend fun put(owner: UUID<Principal>, path: String, value: String) {
        ensureSelfOrSecurityOfficer(owner)
        settingHistory(owner, baseStrings.settings, commonStrings.update, path to value)
        settingTable.put(owner, path, value)
    }

    override suspend fun put(owner: UUID<Principal>, settings: List<Setting>) {
        ensureSelfOrSecurityOfficer(owner)
        for (setting in settings) {
            settingHistory(owner, baseStrings.settings, commonStrings.update, setting.path to setting.value)
            settingTable.put(owner, setting.path, setting.value) // TODO put all settings into one insert
        }
    }

    override suspend fun get(owner: UUID<Principal>, path: String, children: Boolean): List<Setting> {
        ensureSelfOrSecurityOfficer(owner)
        return settingTable.get(owner, path, children)
    }

}