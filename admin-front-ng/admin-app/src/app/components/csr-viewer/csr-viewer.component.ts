import { Component, Input, OnInit } from '@angular/core';
import { CsrDTO } from 'src/app/models/CsrDTO';

@Component({
  selector: 'app-csr-viewer',
  templateUrl: './csr-viewer.component.html',
  styleUrls: ['./csr-viewer.component.scss']
})
export class CsrViewerComponent implements OnInit {

  @Input()
  csr!: CsrDTO;

  constructor() {}

  ngOnInit(): void {
  }

}
