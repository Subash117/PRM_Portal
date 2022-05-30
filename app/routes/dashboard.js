import Route from '@ember/routing/route';
import { service } from '@ember/service';

export default class DashboardRoute extends Route {
  @service router;

  async model() {
    let response = await fetch('http://localhost:8080/PRM_portal/getsession', {
      credentials: 'include',
    });
    let data = await response.json();
    if (!data.session) {
      this.router.transitionTo('login');
    }

    response = await fetch('http://localhost:8080/PRM_portal/getprocessstate', {
      credentials: 'include',
    });
    data = await response.json();

    while (!data.started) {
      response = await fetch(
        'http://localhost:8080/PRM_portal/getprocessstate',
        {
          credentials: 'include',
        }
      );
      data = await response.json();
    }
    return data;
  }
}
