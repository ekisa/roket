import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('GPSLokasyon e2e test', () => {

    let navBarPage: NavBarPage;
    let gPSLokasyonDialogPage: GPSLokasyonDialogPage;
    let gPSLokasyonComponentsPage: GPSLokasyonComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GPSLokasyons', () => {
        navBarPage.goToEntity('gps-lokasyon');
        gPSLokasyonComponentsPage = new GPSLokasyonComponentsPage();
        expect(gPSLokasyonComponentsPage.getTitle())
            .toMatch(/roketApp.gPSLokasyon.home.title/);

    });

    it('should load create GPSLokasyon dialog', () => {
        gPSLokasyonComponentsPage.clickOnCreateButton();
        gPSLokasyonDialogPage = new GPSLokasyonDialogPage();
        expect(gPSLokasyonDialogPage.getModalTitle())
            .toMatch(/roketApp.gPSLokasyon.home.createOrEditLabel/);
        gPSLokasyonDialogPage.close();
    });

    it('should create and save GPSLokasyons', () => {
        gPSLokasyonComponentsPage.clickOnCreateButton();
        gPSLokasyonDialogPage.setEnlemInput('enlem');
        expect(gPSLokasyonDialogPage.getEnlemInput()).toMatch('enlem');
        gPSLokasyonDialogPage.setBoylamInput('boylam');
        expect(gPSLokasyonDialogPage.getBoylamInput()).toMatch('boylam');
        gPSLokasyonDialogPage.save();
        expect(gPSLokasyonDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class GPSLokasyonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-gps-lokasyon div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GPSLokasyonDialogPage {
    modalTitle = element(by.css('h4#myGPSLokasyonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    enlemInput = element(by.css('input#field_enlem'));
    boylamInput = element(by.css('input#field_boylam'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setEnlemInput = function(enlem) {
        this.enlemInput.sendKeys(enlem);
    }

    getEnlemInput = function() {
        return this.enlemInput.getAttribute('value');
    }

    setBoylamInput = function(boylam) {
        this.boylamInput.sendKeys(boylam);
    }

    getBoylamInput = function() {
        return this.boylamInput.getAttribute('value');
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
