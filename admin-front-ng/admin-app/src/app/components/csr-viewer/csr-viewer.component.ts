import { Component, Input, OnInit } from '@angular/core';
import { CSRDTO } from 'src/app/models/CSRDTO';

@Component({
  selector: 'app-csr-viewer',
  templateUrl: './csr-viewer.component.html',
  styleUrls: ['./csr-viewer.component.scss']
})
export class CsrViewerComponent implements OnInit {

  @Input()
  csr!: CSRDTO;

  constructor() {}

  ngOnInit(): void {
  }

}
