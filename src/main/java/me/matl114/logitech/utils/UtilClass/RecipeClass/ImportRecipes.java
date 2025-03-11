package me.matl114.logitech.utils.UtilClass.RecipeClass;

public interface ImportRecipes {
    /**
     * whether the recipe will conflicts with RecipeSupport loader
     * if you are sure that no conflicts will occur, override this with false,and this will be read by recipesupporter
     * @return
     */
    default boolean isConflict(){
        return true;
    }
}
