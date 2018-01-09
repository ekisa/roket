import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Emir e2e test', () => {

    let navBarPage: NavBarPage;
    let emirDialogPage: EmirDialogPage;
    let emirComponentsPage: EmirComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Emirs', () => {
        navBarPage.goToEntity('emir');
        emirComponentsPage = new EmirComponentsPage();
        expect(emirComponentsPage.getTitle())
            .toMatch(/roketApp.emir.home.title/);

    });

    it('should load create Emir dialog', () => {
        emirComponentsPage.clickOnCreateButton();
        emirDialogPage = new EmirDialogPage();
        expect(emirDialogPage.getModalTitle())
            .toMatch(/roketApp.emir.home.createOrEditLabel/);
        emirDialogPage.close();
    });

    it('should create and save Emirs', () => {
        emirComponentsPage.clickOnCreateButton();
        emirDialogPage.statuSelectLastOption();
        emirDialogPage.isyeriSelectLastOption();
        emirDialogPage.adresSelectLastOption();
        emirDialogPage.gpsLokasyonSelectLastOption();
        emirDialogPage.faturaSelectLastOption();
        emirDialogPage.save();
        expect(emirDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EmirComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-emir div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EmirDialogPage {
    modalTitle = element(by.css('h4#myEmirLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    statuSelect = element(by.css('select#field_statu'));
    isyeriSelect = element(by.css('select#field_isyeri'));
    adresSelect = element(by.css('select#field_adres'));
    gpsLokasyonSelect = element(by.css('select#field_gpsLokasyon'));
    faturaSelect = element(by.css('select#field_fatura'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setStatuSelect = function(statu) {
        this.statuSelect.sendKeys(statu);
    }

    getStatuSelect = function() {
        return this.statuSelect.element(by.css('option:checked')).getText();
    }

    statuSelectLastOption = function() {
        this.statuSelect.all(by.tagName('option')).last().click();
    }
    isyeriSelectLastOption = function() {
        this.isyeriSelect.all(by.tagName('option')).last().click();
    }

    isyeriSelectOption = function(option) {
        this.isyeriSelect.sendKeys(option);
    }

    getIsyeriSelect = function() {
        return this.isyeriSelect;
    }

    getIsyeriSelectedOption = function() {
        return this.isyeriSelect.element(by.css('option:checked')).getText();
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

    faturaSelectLastOption = function() {
        this.faturaSelect.all(by.tagName('option')).last().click();
    }

    faturaSelectOption = function(option) {
        this.faturaSelect.sendKeys(option);
    }

    getFaturaSelect = function() {
        return this.faturaSelect;
    }

    getFaturaSelectedOption = function() {
        return this.faturaSelect.element(by.css('option:checked')).getText();
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
