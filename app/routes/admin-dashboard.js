import Route from '@ember/routing/route';
import { service } from '@ember/service';

export default class AdminDashboardRoute extends Route {
  @service router;
  async beforeModel() {
    let response = await fetch('http://localhost:8080/PRM_portal/getsession', {
      credentials: 'include',
    });
    let data = await response.json();
    if (!data.session || data.pid != 'admin') {
      this.router.transitionTo('admin-login');
    }
  }

  async model() {
    let response = await fetch(
      'http://localhost:8080/PRM_portal/getprocessnames',
      { credentials: 'include' }
    );
    let data = await response.json();
    return data;
  }
}
