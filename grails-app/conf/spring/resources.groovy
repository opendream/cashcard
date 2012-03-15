beans = {
    userDetailsService(th.co.opendream.cashcard.CompanyUserDetailsService)
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("th","TH")
        java.util.Locale.setDefault(defaultLocale)
    }
}

