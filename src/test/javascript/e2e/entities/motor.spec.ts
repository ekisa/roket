import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Motor e2e test', () => {

    let navBarPage: NavBarPage;
    let motorDialogPage: MotorDialogPage;
    let motorComponentsPage: MotorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Motors', () => {
        navBarPage.goToEntity('motor');
        motorComponentsPage = new MotorComponentsPage();
        expect(motorComponentsPage.getTitle())
            .toMatch(/roketApp.motor.home.title/);

    });

    it('should load create Motor dialog', () => {
        motorComponentsPage.clickOnCreateButton();
        motorDialogPage = new MotorDialogPage();
        expect(motorDialogPage.getModalTitle())
            .toMatch(/roketApp.motor.home.createOrEditLabel/);
        motorDialogPage.close();
    });

    it('should create and save Motors', () => {
        motorComponentsPage.clickOnCreateButton();
        motorDialogPage.setNumarasiInput('numarasi');
        expect(motorDialogPage.getNumarasiInput()).toMatch('numarasi');
        motorDialogPage.setMarkaInput('marka');
        expect(motorDialogPage.getMarkaInput()).toMatch('marka');
        motorDialogPage.setModelInput('model');
        expect(motorDialogPage.getModelInput()).toMatch('model');
        motorDialogPage.setYilInput('yil');
        expect(motorDialogPage.getYilInput()).toMatch('yil');
        motorDialogPage.save();
        expect(motorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MotorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-motor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MotorDialogPage {
    modalTitle = element(by.css('h4#myMotorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numarasiInput = element(by.css('input#field_numarasi'));
    markaInput = element(by.css('input#field_marka'));
    modelInput = element(by.css('input#field_model'));
    yilInput = element(by.css('input#field_yil'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumarasiInput = function(numarasi) {
        this.numarasiInput.sendKeys(numarasi);
    }

    getNumarasiInput = function() {
        return this.numarasiInput.getAttribute('value');
    }

    setMarkaInput = function(marka) {
        this.markaInput.sendKeys(marka);
    }

    getMarkaInput = function() {
        return this.markaInput.getAttribute('value');
    }

    setModelInput = function(model) {
        this.modelInput.sendKeys(model);
    }

    getModelInput = function() {
        return this.modelInput.getAttribute('value');
    }

    setYilInput = function(yil) {
        this.yilInput.sendKeys(yil);
    }

    getYilInput = function() {
        return this.yilInput.getAttribute('value');
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
