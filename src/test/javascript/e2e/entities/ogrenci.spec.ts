import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Ogrenci e2e test', () => {

    let navBarPage: NavBarPage;
    let ogrenciDialogPage: OgrenciDialogPage;
    let ogrenciComponentsPage: OgrenciComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ogrencis', () => {
        navBarPage.goToEntity('ogrenci');
        ogrenciComponentsPage = new OgrenciComponentsPage();
        expect(ogrenciComponentsPage.getTitle())
            .toMatch(/roketApp.ogrenci.home.title/);

    });

    it('should load create Ogrenci dialog', () => {
        ogrenciComponentsPage.clickOnCreateButton();
        ogrenciDialogPage = new OgrenciDialogPage();
        expect(ogrenciDialogPage.getModalTitle())
            .toMatch(/roketApp.ogrenci.home.createOrEditLabel/);
        ogrenciDialogPage.close();
    });

    it('should create and save Ogrencis', () => {
        ogrenciComponentsPage.clickOnCreateButton();
        ogrenciDialogPage.setAdiInput('adi');
        expect(ogrenciDialogPage.getAdiInput()).toMatch('adi');
        // ogrenciDialogPage.siniflariSelectLastOption();
        ogrenciDialogPage.save();
        expect(ogrenciDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OgrenciComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ogrenci div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OgrenciDialogPage {
    modalTitle = element(by.css('h4#myOgrenciLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adiInput = element(by.css('input#field_adi'));
    siniflariSelect = element(by.css('select#field_siniflari'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdiInput = function(adi) {
        this.adiInput.sendKeys(adi);
    }

    getAdiInput = function() {
        return this.adiInput.getAttribute('value');
    }

    siniflariSelectLastOption = function() {
        this.siniflariSelect.all(by.tagName('option')).last().click();
    }

    siniflariSelectOption = function(option) {
        this.siniflariSelect.sendKeys(option);
    }

    getSiniflariSelect = function() {
        return this.siniflariSelect;
    }

    getSiniflariSelectedOption = function() {
        return this.siniflariSelect.element(by.css('option:checked')).getText();
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
