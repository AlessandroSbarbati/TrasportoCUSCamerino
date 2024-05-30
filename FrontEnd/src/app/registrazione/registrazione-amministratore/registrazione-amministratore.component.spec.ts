import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrazioneAmministratoreComponent } from './registrazione-amministratore.component';

describe('RegistrazioneAmministratoreComponent', () => {
  let component: RegistrazioneAmministratoreComponent;
  let fixture: ComponentFixture<RegistrazioneAmministratoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistrazioneAmministratoreComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistrazioneAmministratoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
