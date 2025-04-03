import { TestBed } from '@angular/core/testing';

import { LanguagesDAOService } from './language-dao.service';

describe('LanguagesDaoService', () => {
  let service: LanguagesDAOService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LanguagesDAOService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
