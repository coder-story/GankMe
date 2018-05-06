package dawn.com.gankme.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import dawn.com.gankme.di.module.LoginModule;

import com.jess.arms.di.scope.ActivityScope;

import dawn.com.gankme.mvp.ui.activity.LoginActivity;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}