import { Component, Input, OnInit } from '@angular/core';
import { ExtendedKeyUsageDTO } from 'src/app/models/ExtendedKeyUsageDTO';

@Component({
  selector: 'app-extended-key-usage',
  templateUrl: './extended-key-usage.component.html',
  styleUrls: ['./extended-key-usage.component.scss']
})
export class ExtendedKeyUsageComponent implements OnInit {

  @Input()
  extendedKeyUsage!: ExtendedKeyUsageDTO;

  constructor() { }

  ngOnInit(): void {
  }

}
