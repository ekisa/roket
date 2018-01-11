import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { EmirGecmisi } from './emir-gecmisi.model';
import { EmirGecmisiService } from './emir-gecmisi.service';

@Injectable()
export class EmirGecmisiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private emirGecmisiService: EmirGecmisiService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.emirGecmisiService.find(id).subscribe((emirGecmisi) => {
                    emirGecmisi.zaman = this.datePipe
                        .transform(emirGecmisi.zaman, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.emirGecmisiModalRef(component, emirGecmisi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.emirGecmisiModalRef(component, new EmirGecmisi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    emirGecmisiModalRef(component: Component, emirGecmisi: EmirGecmisi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emirGecmisi = emirGecmisi;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
