package dawn.com.gankme.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import dawn.com.gankme.di.module.CategoryModule;

import dawn.com.gankme.mvp.ui.fragment.CategoryFragment;

@ActivityScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment fragment);
}