import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Adres e2e test', () => {

    let navBarPage: NavBarPage;
    let adresDialogPage: AdresDialogPage;
    let adresComponentsPage: AdresComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Adres', () => {
        navBarPage.goToEntity('adres');
        adresComponentsPage = new AdresComponentsPage();
        expect(adresComponentsPage.getTitle())
            .toMatch(/roketApp.adres.home.title/);

    });

    it('should load create Adres dialog', () => {
        adresComponentsPage.clickOnCreateButton();
        adresDialogPage = new AdresDialogPage();
        expect(adresDialogPage.getModalTitle())
            .toMatch(/roketApp.adres.home.createOrEditLabel/);
        adresDialogPage.close();
    });

    it('should create and save Adres', () => {
        adresComponentsPage.clickOnCreateButton();
        adresDialogPage.setBbkInput('bbk');
        expect(adresDialogPage.getBbkInput()).toMatch('bbk');
        adresDialogPage.setIckapiNoInput('ickapiNo');
        expect(adresDialogPage.getIckapiNoInput()).toMatch('ickapiNo');
        adresDialogPage.setDiskapiNoInput('diskapiNo');
        expect(adresDialogPage.getDiskapiNoInput()).toMatch('diskapiNo');
        adresDialogPage.setSiteInput('site');
        expect(adresDialogPage.getSiteInput()).toMatch('site');
        adresDialogPage.setSokakInput('sokak');
        expect(adresDialogPage.getSokakInput()).toMatch('sokak');
        adresDialogPage.setCaddeInput('cadde');
        expect(adresDialogPage.getCaddeInput()).toMatch('cadde');
        adresDialogPage.setSemtInput('semt');
        expect(adresDialogPage.getSemtInput()).toMatch('semt');
        adresDialogPage.setAdresTarifiInput('adresTarifi');
        expect(adresDialogPage.getAdresTarifiInput()).toMatch('adresTarifi');
        adresDialogPage.mahalleSelectLastOption();
        adresDialogPage.musteriSelectLastOption();
        adresDialogPage.save();
        expect(adresDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AdresComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-adres div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdresDialogPage {
    modalTitle = element(by.css('h4#myAdresLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bbkInput = element(by.css('input#field_bbk'));
    ickapiNoInput = element(by.css('input#field_ickapiNo'));
    diskapiNoInput = element(by.css('input#field_diskapiNo'));
    siteInput = element(by.css('input#field_site'));
    sokakInput = element(by.css('input#field_sokak'));
    caddeInput = element(by.css('input#field_cadde'));
    semtInput = element(by.css('input#field_semt'));
    adresTarifiInput = element(by.css('input#field_adresTarifi'));
    mahalleSelect = element(by.css('select#field_mahalle'));
    musteriSelect = element(by.css('select#field_musteri'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBbkInput = function(bbk) {
        this.bbkInput.sendKeys(bbk);
    }

    getBbkInput = function() {
        return this.bbkInput.getAttribute('value');
    }

    setIckapiNoInput = function(ickapiNo) {
        this.ickapiNoInput.sendKeys(ickapiNo);
    }

    getIckapiNoInput = function() {
        return this.ickapiNoInput.getAttribute('value');
    }

    setDiskapiNoInput = function(diskapiNo) {
        this.diskapiNoInput.sendKeys(diskapiNo);
    }

    getDiskapiNoInput = function() {
        return this.diskapiNoInput.getAttribute('value');
    }

    setSiteInput = function(site) {
        this.siteInput.sendKeys(site);
    }

    getSiteInput = function() {
        return this.siteInput.getAttribute('value');
    }

    setSokakInput = function(sokak) {
        this.sokakInput.sendKeys(sokak);
    }

    getSokakInput = function() {
        return this.sokakInput.getAttribute('value');
    }

    setCaddeInput = function(cadde) {
        this.caddeInput.sendKeys(cadde);
    }

    getCaddeInput = function() {
        return this.caddeInput.getAttribute('value');
    }

    setSemtInput = function(semt) {
        this.semtInput.sendKeys(semt);
    }

    getSemtInput = function() {
        return this.semtInput.getAttribute('value');
    }

    setAdresTarifiInput = function(adresTarifi) {
        this.adresTarifiInput.sendKeys(adresTarifi);
    }

    getAdresTarifiInput = function() {
        return this.adresTarifiInput.getAttribute('value');
    }

    mahalleSelectLastOption = function() {
        this.mahalleSelect.all(by.tagName('option')).last().click();
    }

    mahalleSelectOption = function(option) {
        this.mahalleSelect.sendKeys(option);
    }

    getMahalleSelect = function() {
        return this.mahalleSelect;
    }

    getMahalleSelectedOption = function() {
        return this.mahalleSelect.element(by.css('option:checked')).getText();
    }

    musteriSelectLastOption = function() {
        this.musteriSelect.all(by.tagName('option')).last().click();
    }

    musteriSelectOption = function(option) {
        this.musteriSelect.sendKeys(option);
    }

    getMusteriSelect = function() {
        return this.musteriSelect;
    }

    getMusteriSelectedOption = function() {
        return this.musteriSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
