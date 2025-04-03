import { TestBed } from '@angular/core/testing';

import { CategoriesDAOService } from './categories-dao.service';

describe('CategoriesDaoService', () => {
  let service: CategoriesDAOService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriesDAOService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
