import { ICategoryAgent, NewCategoryAgent } from './category-agent.model';

export const sampleWithRequiredData: ICategoryAgent = {
  id: 5957,
};

export const sampleWithPartialData: ICategoryAgent = {
  id: 86504,
};

export const sampleWithFullData: ICategoryAgent = {
  id: 14593,
  idAgent: 97554,
  idCategory: 93322,
};

export const sampleWithNewData: NewCategoryAgent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
