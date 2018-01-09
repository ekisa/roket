import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Isyeri e2e test', () => {

    let navBarPage: NavBarPage;
    let isyeriDialogPage: IsyeriDialogPage;
    let isyeriComponentsPage: IsyeriComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Isyeris', () => {
        navBarPage.goToEntity('isyeri');
        isyeriComponentsPage = new IsyeriComponentsPage();
        expect(isyeriComponentsPage.getTitle())
            .toMatch(/roketApp.isyeri.home.title/);

    });

    it('should load create Isyeri dialog', () => {
        isyeriComponentsPage.clickOnCreateButton();
        isyeriDialogPage = new IsyeriDialogPage();
        expect(isyeriDialogPage.getModalTitle())
            .toMatch(/roketApp.isyeri.home.createOrEditLabel/);
        isyeriDialogPage.close();
    });

    it('should create and save Isyeris', () => {
        isyeriComponentsPage.clickOnCreateButton();
        isyeriDialogPage.setAdiInput('adi');
        expect(isyeriDialogPage.getAdiInput()).toMatch('adi');
        isyeriDialogPage.setTelefonInput('telefon');
        expect(isyeriDialogPage.getTelefonInput()).toMatch('telefon');
        isyeriDialogPage.setAciklamaInput('aciklama');
        expect(isyeriDialogPage.getAciklamaInput()).toMatch('aciklama');
        isyeriDialogPage.merkezSelectLastOption();
        isyeriDialogPage.gpsLokasyonSelectLastOption();
        isyeriDialogPage.adresSelectLastOption();
        isyeriDialogPage.musteriSelectLastOption();
        isyeriDialogPage.save();
        expect(isyeriDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IsyeriComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-isyeri div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IsyeriDialogPage {
    modalTitle = element(by.css('h4#myIsyeriLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adiInput = element(by.css('input#field_adi'));
    telefonInput = element(by.css('input#field_telefon'));
    aciklamaInput = element(by.css('input#field_aciklama'));
    merkezSelect = element(by.css('select#field_merkez'));
    gpsLokasyonSelect = element(by.css('select#field_gpsLokasyon'));
    adresSelect = element(by.css('select#field_adres'));
    musteriSelect = element(by.css('select#field_musteri'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdiInput = function(adi) {
        this.adiInput.sendKeys(adi);
    }

    getAdiInput = function() {
        return this.adiInput.getAttribute('value');
    }

    setTelefonInput = function(telefon) {
        this.telefonInput.sendKeys(telefon);
    }

    getTelefonInput = function() {
        return this.telefonInput.getAttribute('value');
    }

    setAciklamaInput = function(aciklama) {
        this.aciklamaInput.sendKeys(aciklama);
    }

    getAciklamaInput = function() {
        return this.aciklamaInput.getAttribute('value');
    }

    merkezSelectLastOption = function() {
        this.merkezSelect.all(by.tagName('option')).last().click();
    }

    merkezSelectOption = function(option) {
        this.merkezSelect.sendKeys(option);
    }

    getMerkezSelect = function() {
        return this.merkezSelect;
    }

    getMerkezSelectedOption = function() {
        return this.merkezSelect.element(by.css('option:checked')).getText();
    }

    gpsLokasyonSelectLastOption = function() {
        this.gpsLokasyonSelect.all(by.tagName('option')).last().click();
    }

    gpsLokasyonSelectOption = function(option) {
        this.gpsLokasyonSelect.sendKeys(option);
    }

    getGpsLokasyonSelect = function() {
        return this.gpsLokasyonSelect;
    }

    getGpsLokasyonSelectedOption = function() {
        return this.gpsLokasyonSelect.element(by.css('option:checked')).getText();
    }

    adresSelectLastOption = function() {
        this.adresSelect.all(by.tagName('option')).last().click();
    }

    adresSelectOption = function(option) {
        this.adresSelect.sendKeys(option);
    }

    getAdresSelect = function() {
        return this.adresSelect;
    }

    getAdresSelectedOption = function() {
        return this.adresSelect.element(by.css('option:checked')).getText();
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
