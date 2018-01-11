import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Isci e2e test', () => {

    let navBarPage: NavBarPage;
    let isciDialogPage: IsciDialogPage;
    let isciComponentsPage: IsciComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Iscis', () => {
        navBarPage.goToEntity('isci');
        isciComponentsPage = new IsciComponentsPage();
        expect(isciComponentsPage.getTitle())
            .toMatch(/roketApp.isci.home.title/);

    });

    it('should load create Isci dialog', () => {
        isciComponentsPage.clickOnCreateButton();
        isciDialogPage = new IsciDialogPage();
        expect(isciDialogPage.getModalTitle())
            .toMatch(/roketApp.isci.home.createOrEditLabel/);
        isciDialogPage.close();
    });

    it('should create and save Iscis', () => {
        isciComponentsPage.clickOnCreateButton();
        isciDialogPage.setAdiInput('adi');
        expect(isciDialogPage.getAdiInput()).toMatch('adi');
        isciDialogPage.setSoyadiInput('soyadi');
        expect(isciDialogPage.getSoyadiInput()).toMatch('soyadi');
        isciDialogPage.setEpostaInput('eposta');
        expect(isciDialogPage.getEpostaInput()).toMatch('eposta');
        isciDialogPage.setTelefonInput('telefon');
        expect(isciDialogPage.getTelefonInput()).toMatch('telefon');
        isciDialogPage.setTeminatTutariInput('5');
        expect(isciDialogPage.getTeminatTutariInput()).toMatch('5');
        isciDialogPage.setMaasInput('5');
        expect(isciDialogPage.getMaasInput()).toMatch('5');
        isciDialogPage.setSicilInput('sicil');
        expect(isciDialogPage.getSicilInput()).toMatch('sicil');
        isciDialogPage.setTcknInput('tckn');
        expect(isciDialogPage.getTcknInput()).toMatch('tckn');
        isciDialogPage.setZimmetliMallarInput('zimmetliMallar');
        expect(isciDialogPage.getZimmetliMallarInput()).toMatch('zimmetliMallar');
        isciDialogPage.motorSelectLastOption();
        isciDialogPage.save();
        expect(isciDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IsciComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-isci div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IsciDialogPage {
    modalTitle = element(by.css('h4#myIsciLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adiInput = element(by.css('input#field_adi'));
    soyadiInput = element(by.css('input#field_soyadi'));
    epostaInput = element(by.css('input#field_eposta'));
    telefonInput = element(by.css('input#field_telefon'));
    teminatTutariInput = element(by.css('input#field_teminatTutari'));
    maasInput = element(by.css('input#field_maas'));
    sicilInput = element(by.css('input#field_sicil'));
    tcknInput = element(by.css('input#field_tckn'));
    zimmetliMallarInput = element(by.css('input#field_zimmetliMallar'));
    motorSelect = element(by.css('select#field_motor'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdiInput = function(adi) {
        this.adiInput.sendKeys(adi);
    }

    getAdiInput = function() {
        return this.adiInput.getAttribute('value');
    }

    setSoyadiInput = function(soyadi) {
        this.soyadiInput.sendKeys(soyadi);
    }

    getSoyadiInput = function() {
        return this.soyadiInput.getAttribute('value');
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

    setTeminatTutariInput = function(teminatTutari) {
        this.teminatTutariInput.sendKeys(teminatTutari);
    }

    getTeminatTutariInput = function() {
        return this.teminatTutariInput.getAttribute('value');
    }

    setMaasInput = function(maas) {
        this.maasInput.sendKeys(maas);
    }

    getMaasInput = function() {
        return this.maasInput.getAttribute('value');
    }

    setSicilInput = function(sicil) {
        this.sicilInput.sendKeys(sicil);
    }

    getSicilInput = function() {
        return this.sicilInput.getAttribute('value');
    }

    setTcknInput = function(tckn) {
        this.tcknInput.sendKeys(tckn);
    }

    getTcknInput = function() {
        return this.tcknInput.getAttribute('value');
    }

    setZimmetliMallarInput = function(zimmetliMallar) {
        this.zimmetliMallarInput.sendKeys(zimmetliMallar);
    }

    getZimmetliMallarInput = function() {
        return this.zimmetliMallarInput.getAttribute('value');
    }

    motorSelectLastOption = function() {
        this.motorSelect.all(by.tagName('option')).last().click();
    }

    motorSelectOption = function(option) {
        this.motorSelect.sendKeys(option);
    }

    getMotorSelect = function() {
        return this.motorSelect;
    }

    getMotorSelectedOption = function() {
        return this.motorSelect.element(by.css('option:checked')).getText();
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
