import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { KuryeGecmisi } from './kurye-gecmisi.model';
import { KuryeGecmisiService } from './kurye-gecmisi.service';

@Injectable()
export class KuryeGecmisiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private kuryeGecmisiService: KuryeGecmisiService

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
                this.kuryeGecmisiService.find(id).subscribe((kuryeGecmisi) => {
                    kuryeGecmisi.zaman = this.datePipe
                        .transform(kuryeGecmisi.zaman, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.kuryeGecmisiModalRef(component, kuryeGecmisi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.kuryeGecmisiModalRef(component, new KuryeGecmisi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    kuryeGecmisiModalRef(component: Component, kuryeGecmisi: KuryeGecmisi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.kuryeGecmisi = kuryeGecmisi;
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
