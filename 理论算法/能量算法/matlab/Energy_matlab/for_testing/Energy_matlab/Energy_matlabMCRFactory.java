/*
 * MATLAB Compiler: 6.6 (R2018a)
 * Date: Fri Jun  5 19:22:19 2020
 * Arguments: 
 * "-B""macro_default""-W""java:Energy_matlab,Class1""-T""link:lib""-d""I:\\研究生阶段\\矿山\\理论算法\\能量算法\\matlab\\Energy_matlab\\for_testing""class{Class1:I:\\研究生阶段\\矿山\\理论算法\\能量算法\\matlab\\emd.m,I:\\研究生阶段\\矿山\\理论算法\\能量算法\\matlab\\Energy.m}"
 */

package Energy_matlab;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;

/**
 * <i>INTERNAL USE ONLY</i>
 */
public class Energy_matlabMCRFactory
{
   
    
    /** Component's uuid */
    private static final String sComponentId = "Energy_matla_7F0227DF310A8445DFC4F77894F04AF2";
    
    /** Component name */
    private static final String sComponentName = "Energy_matlab";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(Energy_matlabMCRFactory.class)
        );
    
    
    private Energy_matlabMCRFactory()
    {
        // Never called.
    }
    
    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException
    {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }
        return MWMCR.newInstance(
            componentOptions, 
            Energy_matlabMCRFactory.class, 
            sComponentName, 
            sComponentId,
            new int[]{9,4,0}
        );
    }
    
    public static MWMCR newInstance() throws MWException
    {
        return newInstance(sDefaultComponentOptions);
    }
}
