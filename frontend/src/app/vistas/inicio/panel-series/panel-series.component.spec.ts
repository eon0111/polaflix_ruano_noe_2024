import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelSeriesComponent } from './panel-series.component';

describe('PanelSeriesComponent', () => {
  let component: PanelSeriesComponent;
  let fixture: ComponentFixture<PanelSeriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelSeriesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PanelSeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
