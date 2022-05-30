import Route from '@ember/routing/route';

export default class StartProcessRoute extends Route {
  async beforeModel() {
    let response = await fetch('http://localhost:8080/PRM_portal/getsession', {
      credentials: 'include',
    });
    let data = await response.json();
    if (!data.session || data.pid != 'admin') {
      this.router.transitionTo('admin-login');
    }
  }

  async model(params) {
    let response = await fetch(
      `http://localhost:8080/PRM_portal/startprocess?pid=${params.pid}`
    );
    let data = await response.json();
    data.pid = params.pid;
    return data;
  }
}
