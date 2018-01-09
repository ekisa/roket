import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Merkez e2e test', () => {

    let navBarPage: NavBarPage;
    let merkezDialogPage: MerkezDialogPage;
    let merkezComponentsPage: MerkezComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Merkezs', () => {
        navBarPage.goToEntity('merkez');
        merkezComponentsPage = new MerkezComponentsPage();
        expect(merkezComponentsPage.getTitle())
            .toMatch(/roketApp.merkez.home.title/);

    });

    it('should load create Merkez dialog', () => {
        merkezComponentsPage.clickOnCreateButton();
        merkezDialogPage = new MerkezDialogPage();
        expect(merkezDialogPage.getModalTitle())
            .toMatch(/roketApp.merkez.home.createOrEditLabel/);
        merkezDialogPage.close();
    });

    it('should create and save Merkezs', () => {
        merkezComponentsPage.clickOnCreateButton();
        merkezDialogPage.setAdiInput('adi');
        expect(merkezDialogPage.getAdiInput()).toMatch('adi');
        merkezDialogPage.adresSelectLastOption();
        merkezDialogPage.gpsLokasyonSelectLastOption();
        merkezDialogPage.mahallelerSelectLastOption();
        merkezDialogPage.save();
        expect(merkezDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MerkezComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-merkez div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MerkezDialogPage {
    modalTitle = element(by.css('h4#myMerkezLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adiInput = element(by.css('input#field_adi'));
    adresSelect = element(by.css('select#field_adres'));
    gpsLokasyonSelect = element(by.css('select#field_gpsLokasyon'));
    mahallelerSelect = element(by.css('select#field_mahalleler'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdiInput = function(adi) {
        this.adiInput.sendKeys(adi);
    }

    getAdiInput = function() {
        return this.adiInput.getAttribute('value');
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

    mahallelerSelectLastOption = function() {
        this.mahallelerSelect.all(by.tagName('option')).last().click();
    }

    mahallelerSelectOption = function(option) {
        this.mahallelerSelect.sendKeys(option);
    }

    getMahallelerSelect = function() {
        return this.mahallelerSelect;
    }

    getMahallelerSelectedOption = function() {
        return this.mahallelerSelect.element(by.css('option:checked')).getText();
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
