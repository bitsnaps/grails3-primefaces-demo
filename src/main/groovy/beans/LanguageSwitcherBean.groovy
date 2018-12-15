package com.company.demo.beans

import grails.plugins.primefaces.MessageSourceBean
import javax.faces.bean.ManagedBean
import javax.faces.bean.ManagedProperty

@ManagedBean
class LanguageSwitcherBean {

    @ManagedProperty(value = "#{message}")
    MessageSourceBean message

    String selectedLanguage
    List languages = ['it', 'en']

    String getSelectedLanguage() {
        message.locale.language
    }

    void setSelectedLanguage(String selectedLanguage) {
        message.locale = selectedLanguage
        this.selectedLanguage = selectedLanguage
    }


}
