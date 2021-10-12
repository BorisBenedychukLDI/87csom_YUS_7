package com.kosmos.mos.lot.com.ApplicationPackage87csom

import android.app.Application
import android.content.Context

class Application87csom : Application (), ApplicaitonContract87csom.ApplicationMethods87csom {

    private lateinit var applicationSetupMethods87csom: ApplicaitonContract87csom.ApplicationSetupMethods87csom

    override fun onCreate() {
        applicationSetupMethods87csom = ApplicationSetuper87csom(this)
        applicationSetupMethods87csom.afSetup87csom()
        applicationSetupMethods87csom.maSetup87csom()
        applicationSetupMethods87csom.osSetup87csom()
        super.onCreate()
    }

    override fun context87csom(): Context = this
}