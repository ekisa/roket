import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Mahalle e2e test', () => {

    let navBarPage: NavBarPage;
    let mahalleDialogPage: MahalleDialogPage;
    let mahalleComponentsPage: MahalleComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Mahalles', () => {
        navBarPage.goToEntity('mahalle');
        mahalleComponentsPage = new MahalleComponentsPage();
        expect(mahalleComponentsPage.getTitle())
            .toMatch(/roketApp.mahalle.home.title/);

    });

    it('should load create Mahalle dialog', () => {
        mahalleComponentsPage.clickOnCreateButton();
        mahalleDialogPage = new MahalleDialogPage();
        expect(mahalleDialogPage.getModalTitle())
            .toMatch(/roketApp.mahalle.home.createOrEditLabel/);
        mahalleDialogPage.close();
    });

    it('should create and save Mahalles', () => {
        mahalleComponentsPage.clickOnCreateButton();
        mahalleDialogPage.setMahalleAdInput('mahalleAd');
        expect(mahalleDialogPage.getMahalleAdInput()).toMatch('mahalleAd');
        mahalleDialogPage.setPostaKoduInput('postaKodu');
        expect(mahalleDialogPage.getPostaKoduInput()).toMatch('postaKodu');
        mahalleDialogPage.setSemtInput('semt');
        expect(mahalleDialogPage.getSemtInput()).toMatch('semt');
        mahalleDialogPage.save();
        expect(mahalleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MahalleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-mahalle div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MahalleDialogPage {
    modalTitle = element(by.css('h4#myMahalleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    mahalleAdInput = element(by.css('input#field_mahalleAd'));
    postaKoduInput = element(by.css('input#field_postaKodu'));
    semtInput = element(by.css('input#field_semt'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMahalleAdInput = function(mahalleAd) {
        this.mahalleAdInput.sendKeys(mahalleAd);
    }

    getMahalleAdInput = function() {
        return this.mahalleAdInput.getAttribute('value');
    }

    setPostaKoduInput = function(postaKodu) {
        this.postaKoduInput.sendKeys(postaKodu);
    }

    getPostaKoduInput = function() {
        return this.postaKoduInput.getAttribute('value');
    }

    setSemtInput = function(semt) {
        this.semtInput.sendKeys(semt);
    }

    getSemtInput = function() {
        return this.semtInput.getAttribute('value');
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
