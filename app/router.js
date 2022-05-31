import EmberRouter from '@ember/routing/router';
import config from 'prmportal/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('admin-login');
  this.route('login');
  this.route('signup', { path: '/signup/:pid' });
  this.route('admin-dashboard');
  this.route('create-process');
  this.route('dashboard');
  this.route('start-process', { path: '/process/:pid' });
  this.route('answers');
  this.route('viewquestions', { path: '/viewquestions/:uid' });
  this.route('dashboard-loading');
  this.route('notfound', { path: '/*path' });
  this.route('finished');
});
