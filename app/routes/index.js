import Route from '@ember/routing/route';

export default class IndexRoute extends Route {
  async model() {
    let response = await fetch(
      'http://localhost:8080/PRM_portal/getprocesshome',
      { credentials: 'include' }
    );
    let data = await response.json();
    return data;
  }
}
