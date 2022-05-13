import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RootLayoutPageComponent } from './root-layout-page.component';

describe('RootLayoutPageComponent', () => {
  let component: RootLayoutPageComponent;
  let fixture: ComponentFixture<RootLayoutPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RootLayoutPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RootLayoutPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
