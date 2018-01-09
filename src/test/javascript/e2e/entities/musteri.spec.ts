import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Musteri e2e test', () => {

    let navBarPage: NavBarPage;
    let musteriDialogPage: MusteriDialogPage;
    let musteriComponentsPage: MusteriComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Musteris', () => {
        navBarPage.goToEntity('musteri');
        musteriComponentsPage = new MusteriComponentsPage();
        expect(musteriComponentsPage.getTitle())
            .toMatch(/roketApp.musteri.home.title/);

    });

    it('should load create Musteri dialog', () => {
        musteriComponentsPage.clickOnCreateButton();
        musteriDialogPage = new MusteriDialogPage();
        expect(musteriDialogPage.getModalTitle())
            .toMatch(/roketApp.musteri.home.createOrEditLabel/);
        musteriDialogPage.close();
    });

    it('should create and save Musteris', () => {
        musteriComponentsPage.clickOnCreateButton();
        musteriDialogPage.setUnvanInput('unvan');
        expect(musteriDialogPage.getUnvanInput()).toMatch('unvan');
        musteriDialogPage.setEpostaInput('eposta');
        expect(musteriDialogPage.getEpostaInput()).toMatch('eposta');
        musteriDialogPage.setTelefonInput('telefon');
        expect(musteriDialogPage.getTelefonInput()).toMatch('telefon');
        musteriDialogPage.adresSelectLastOption();
        musteriDialogPage.save();
        expect(musteriDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MusteriComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-musteri div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MusteriDialogPage {
    modalTitle = element(by.css('h4#myMusteriLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    unvanInput = element(by.css('input#field_unvan'));
    epostaInput = element(by.css('input#field_eposta'));
    telefonInput = element(by.css('input#field_telefon'));
    adresSelect = element(by.css('select#field_adres'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUnvanInput = function(unvan) {
        this.unvanInput.sendKeys(unvan);
    }

    getUnvanInput = function() {
        return this.unvanInput.getAttribute('value');
    }

    setEpostaInput = function(eposta) {
        this.epostaInput.sendKeys(eposta);
    }

    getEpostaInput = function() {
        return this.epostaInput.getAttribute('value');
    }

    setTelefonInput = function(telefon) {
        this.telefonInput.sendKeys(telefon);
    }

    getTelefonInput = function() {
        return this.telefonInput.getAttribute('value');
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
