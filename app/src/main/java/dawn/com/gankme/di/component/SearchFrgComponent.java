package dawn.com.gankme.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import dawn.com.gankme.di.module.SearchFrgModule;
import dawn.com.gankme.mvp.ui.fragment.GankListFragment;
import dawn.com.gankme.mvp.ui.fragment.SearchFragment;


@FragmentScope
@Component(modules = SearchFrgModule.class, dependencies = AppComponent.class)
public interface SearchFrgComponent {

    void inject(SearchFragment frg);
}