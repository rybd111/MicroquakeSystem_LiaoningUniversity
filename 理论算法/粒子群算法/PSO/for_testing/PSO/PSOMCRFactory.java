/*
 * MATLAB Compiler: 6.6 (R2018a)
 * Date: Wed Aug  5 10:52:58 2020
 * Arguments: 
 * "-B""macro_default""-W""java:PSO,pso_locate""-T""link:lib""-d""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\PSO\\for_testing""class{pso_locate:I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mubiao3.m,I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mysort3.m,I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\PSO.m}""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mubiao3.m""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mysort3.m""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\PSO.m"
 */

package PSO;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;

/**
 * <i>INTERNAL USE ONLY</i>
 */
public class PSOMCRFactory
{
   
    
    /** Component's uuid */
    private static final String sComponentId = "PSO_261911D7E415AB33F37250EC87E610AB";
    
    /** Component name */
    private static final String sComponentName = "PSO";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(PSOMCRFactory.class)
        );
    
    
    private PSOMCRFactory()
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
            PSOMCRFactory.class, 
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
